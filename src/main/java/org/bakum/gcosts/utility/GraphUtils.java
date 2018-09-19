package org.bakum.gcosts.utility;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;

import java.util.*;
import java.util.Map.Entry;

/**
 * Utilities for managing graphs. Leverages the JGraphT library.
 *
 * @author James Kojo
 */
public class GraphUtils {

    /**
     * replace the vertices in the graph with an alternate set of vertices.
     *
     * @param graph      graph to be mutated
     * @param alternates alternate vertices to insert into the graph. map of vertices to their direct dependents.
     */
    public static <V> void swapVertices(DirectedWeightedPseudograph<V, DefaultWeightedEdge> graph, Map<V, Set<V>> alternates) {
        Objects.requireNonNull(graph, "graph");
        Objects.requireNonNull(alternates, "alternates");
        // add all of the new vertices to prep for linking
        addAllVertices(graph, alternates.keySet());

        for (Entry<V, Set<V>> entry : alternates.entrySet()) {
            V alternateVertex = entry.getKey();
            Set<V> dependencies = entry.getValue();
            // make sure we can satisfy all dependencies before continuing
            // TODO: this should probably done outside so it can be handled better.
            if (!graph.vertexSet().containsAll(dependencies)) {
                continue;
            }

            // figure out which vertices depend on the incumbent vertex
            Set<V> dependents = Collections.emptySet();
            if (graph.containsVertex(alternateVertex)) {
                dependents = getIncomingVertices(graph, alternateVertex);
                graph.removeVertex(alternateVertex);
            }
            // (re)insert the vertex and re-link the dependents
            graph.addVertex(alternateVertex);
            addIncomingEdges(graph, alternateVertex, dependents);

            // create the dependencies
            addOutgoingEdges(graph, alternateVertex, dependencies);
        }
    }

    /**
     * Add all of the vertices to the graph without any edges. If the the graph
     * already contains any one of the vertices, that vertex is not added.
     *
     * @param graph    graph to be mutated
     * @param vertices vertices to add
     */
    public static <V> void addAllVertices(DirectedWeightedPseudograph<V, DefaultWeightedEdge> graph, Set<V> vertices) {
        // add all of the new vertices to prep for linking
        for (V vertex : vertices) {
            graph.addVertex(vertex);
        }
    }

    /**
     * Add dependencies to the given source vertex. Whether duplicates are created is dependent
     * on the underlying {@link DirectedWeightedPseudograph} implementation.
     *
     * @param graph   graph to be mutated
     * @param source  source vertex of the new edges
     * @param targets target vertices for the new edges
     */
    public static <V> void addOutgoingEdges(DirectedWeightedPseudograph<V, DefaultWeightedEdge> graph, V source, Set<V> targets) {
        if (!graph.containsVertex(source)) {
            graph.addVertex(source);
        }
        for (V target : targets) {
            if (!graph.containsVertex(target)) {
                graph.addVertex(target);
            }
            graph.addEdge(source, target);
        }
    }

    /**
     * Add dependents on the give target vertex. Whether duplicates are created is dependent
     * on the underlying {@link DirectedWeightedPseudograph} implementation.
     *
     * @param graph   graph to be mutated
     * @param target  target vertex for the new edges
     * @param sources source vertices for the new edges
     */
    public static <N> void addIncomingEdges(DirectedWeightedPseudograph<N, DefaultWeightedEdge> graph, N target, Set<N> sources) {
        if (!graph.containsVertex(target)) {
            graph.addVertex(target);
        }
        for (N source : sources) {
            if (!graph.containsVertex(source)) {
                graph.addVertex(source);
            }
            graph.addEdge(source, target);
        }
    }

    /**
     * Fetch all of the dependents of the given target vertex
     *
     * @return mutable snapshot of the source vertices of all incoming edges
     */
    public static <V> Set<V> getIncomingVertices(DirectedWeightedPseudograph<V, DefaultWeightedEdge> graph, V target) {
        Set<DefaultWeightedEdge> edges = graph.incomingEdgesOf(target);
        Set<V> sources = new LinkedHashSet<V>();
        for (DefaultWeightedEdge edge : edges) {
            sources.add(graph.getEdgeSource(edge));
        }
        return sources;
    }

    /**
     * Fetch all of the dependencies of the given source vertex
     *
     * @return mutable snapshot of the target vertices of all outgoing edges
     */
    public static <V> Set<V> getOutgoingVertices(DirectedWeightedPseudograph<V, DefaultWeightedEdge> graph, V source) {
        Set<DefaultWeightedEdge> edges = graph.outgoingEdgesOf(source);
        Set<V> targets = new LinkedHashSet<V>();
        for (DefaultWeightedEdge edge : edges) {
            targets.add(graph.getEdgeTarget(edge));
        }
        return targets;
    }

    /**
     * Copy the source graph into the target graph
     */
    public static <V> void copyGraph(DirectedWeightedPseudograph<V, DefaultWeightedEdge> sourceGraph, DirectedWeightedPseudograph<V, DefaultWeightedEdge> targetGraph) {
        addAllVertices(targetGraph, sourceGraph.vertexSet());
        for (DefaultWeightedEdge edge : sourceGraph.edgeSet()) {
            targetGraph.addEdge(sourceGraph.getEdgeSource(edge), sourceGraph.getEdgeTarget(edge));
        }
    }

    /**
     * Find the leave vertices in the graph. I.E. Vertices that have no outgoing edges
     *
     * @param graph graph to search
     * @return mutable snapshot of all leaf vertices.
     */
    public static <V> Set<V> getLeafVertices(DirectedWeightedPseudograph<V, DefaultWeightedEdge> graph) {
        Set<V> vertexSet = graph.vertexSet();
        Set<V> leaves = new HashSet<V>(vertexSet.size() * 2);
        for (V vertex : vertexSet) {
            if (graph.outgoingEdgesOf(vertex).isEmpty()) {
                leaves.add(vertex);
            }
        }
        return leaves;
    }

    /**
     * Removes vertices from graph
     *
     * @param graph    raph to mutate
     * @param vertices vertices to remove
     */
    public static <V> void removeVertices(DirectedWeightedPseudograph<V, DefaultWeightedEdge> graph, Set<V> vertices) {
        for (V vertex : vertices) {
            if (graph.containsVertex(vertex)) {
                graph.removeVertex(vertex);
            }
        }
    }
}
