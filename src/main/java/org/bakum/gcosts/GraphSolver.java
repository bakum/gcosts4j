package org.bakum.gcosts;

import org.bakum.gcosts.enumeration.Currency;
import org.bakum.gcosts.enumeration.FlowDirection;
import org.bakum.gcosts.enumeration.FlowType;
import org.jgrapht.io.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphSolver {
    private static GraphSolver instance;
    private Currency currency;            //Валюта расчета
    private Double rate;                  //Курс

    public static GraphSolver getInstance() {
        if (instance == null) {
            instance = new GraphSolver();
        }
        return instance;
    }

    private GraphSolver() {
        this.currency = Currency.UAH;
        this.rate = 1.0;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        if (this.currency != currency) {
            this.rate = 0.0;
        }
        this.currency = currency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public void setCurrencyRate(Currency currency, Double rate) {
        this.currency = currency;
        this.rate = rate;
    }

    public Gcosts<Node, Flow> loadFromFile(String fn, boolean withData) throws FileNotFoundException, ImportException {
        /*
         * Create vertex provider.
         *
         * The importer reads vertices and calls a vertex provider to create them. The provider
         * receives as input the unique id of each vertex and any additional attributes from the
         * input stream.
         */
        VertexProvider<Node> vertexProvider = (id, attributes) -> {
            Node cv = new Node(id);

            // read color from attributes map
            if (attributes.containsKey("name")) {
                String name = attributes.get("name").getValue();
                cv.setName(name);
            }

            if (attributes.containsKey("desc")) {
                String desc = attributes.get("desc").getValue();
                cv.setDescription(desc);
            }

            return cv;
        };

        /*
         * Create edge provider.
         *
         * The importer reads edges from the input stream and calls an edge provider to create them.
         * The provider receives as input the source and target vertex of the edge, an edge label
         * (which can be null) and a set of edge attributes all read from the input stream.
         */
        EdgeProvider<Node, Flow> edgeProvider = (from, to, label, attributes) -> {
            Flow fl = new Flow();
            Costs c = new Costs();
            if (withData) {
                if (attributes.containsKey("quantity")) {
                    Double q = Double.parseDouble(attributes.get("quantity").getValue());
                    c.setQuantity(q);
                }
                if (attributes.containsKey("price")) {
                    Double price = Double.parseDouble(attributes.get("price").getValue());
                    c.setPrice(price);
                }
            }

            if (attributes.containsKey("direction")) {
                String d = attributes.get("direction").getValue();
                switch (d) {
                    case "Flow of the costs":
                        fl.setDirection(FlowDirection.FLOW);
                        break;
                    case "Begin value of the costs":
                        fl.setDirection(FlowDirection.BEGIN_VALUE);
                        break;
                    case "End value of the costs":
                        fl.setDirection(FlowDirection.END_VALUE);
                        break;
                    default:
                        // ignore not supported
                }

            }
            if (attributes.containsKey("type")) {
                String d = attributes.get("type").getValue();
                switch (d) {
                    case "Primary":
                        fl.setType(FlowType.PRIMARY);
                        break;
                    case "Secondary":
                        fl.setType(FlowType.SECONDARY);
                        break;
                    default:
                        // ignore not supported
                }

            }

            fl.setCost(c);

            return fl;
        };

        GraphMLImporter<Node, Flow> importer =
                new GraphMLImporter<>(vertexProvider, edgeProvider);

        Gcosts<Node, Flow> graph = new Gcosts<>(Flow.class);
        importer.importGraph(graph, new FileReader(fn));

        return graph;
    }

    public void saveToFile(Gcosts<Node, Flow> graph, String fn) throws IOException, ExportException {
        /*
         * Create vertex id provider.
         *
         * The exporter needs to generate for each vertex a unique identifier.
         */
        ComponentNameProvider<Node> vertexIdProvider = v -> v.getGuid();
        /*
         * Create vertex label provider.
         *
         * The exporter may need to generate for each vertex a (not necessarily unique) label. If
         * null the exporter does not output any labels.
         */
        ComponentNameProvider<Node> vertexLabelProvider = v -> v.getName();
        /*
         * The exporter may need to generate for each vertex a set of attributes. Attributes must
         * also be registered as shown later on.
         */
        ComponentAttributeProvider<Node> vertexAttributeProvider = v -> {
            Map<String, Attribute> m = new HashMap<>();
            if (v.getDescription() != null) {
                m.put("desc", DefaultAttribute.createAttribute(v.getDescription()));
            }
            m.put("name", DefaultAttribute.createAttribute(v.getName()));
            return m;
        };

        /*
         * Create edge id provider.
         *
         * The exporter needs to generate for each edge a unique identifier.
         */
        ComponentNameProvider<Flow> edgeIdProvider =
                new IntegerComponentNameProvider<>();

        /*
         * Create edge label provider.
         *
         * The exporter may need to generate for each edge a (not necessarily unique) label. If null
         * the exporter does not output any labels.
         */
        ComponentNameProvider<Flow> edgeLabelProvider = null;

        /*
         * The exporter may need to generate for each edge a set of attributes. Attributes must also
         * be registered as shown later on.
         */
        ComponentAttributeProvider<Flow> edgeAttributeProvider = e -> {
            Map<String, Attribute> m = new HashMap<>();
            if (e.getCost() != null) {
                m.put("quantity", DefaultAttribute.createAttribute(e.getCost().getQuantity()));
                m.put("price", DefaultAttribute.createAttribute(e.getCost().getPrice()));
            }
            m.put("name", DefaultAttribute.createAttribute(e.toString()));
            m.put("direction", DefaultAttribute.createAttribute(e.getDirection().desc()));
            m.put("type", DefaultAttribute.createAttribute(e.getType().desc()));
            return m;
        };

        /*
         * Create the exporter
         */
        GraphMLExporter<Node,
                Flow> exporter = new GraphMLExporter<>(
                vertexIdProvider, vertexLabelProvider, vertexAttributeProvider, edgeIdProvider,
                edgeLabelProvider, edgeAttributeProvider);

        /*
         * Set to export the internal edge weights
         */
        exporter.setExportEdgeWeights(true);

        /*
         * Register additional attributes for vertices
         */
        exporter.registerAttribute("desc", GraphMLExporter.AttributeCategory.NODE, AttributeType.STRING);
        exporter.registerAttribute("name", GraphMLExporter.AttributeCategory.NODE, AttributeType.STRING);

        /*
         * Register additional attributes for edges
         */
        exporter.registerAttribute("quantity", GraphMLExporter.AttributeCategory.EDGE, AttributeType.DOUBLE);
        exporter.registerAttribute("price", GraphMLExporter.AttributeCategory.EDGE, AttributeType.DOUBLE);
        exporter.registerAttribute("direction", GraphMLExporter.AttributeCategory.EDGE, AttributeType.STRING);
        exporter.registerAttribute("type", GraphMLExporter.AttributeCategory.EDGE, AttributeType.STRING);

        /*
         * Register additional name attribute for vertices and edges
         */


        FileWriter w;
        w = new FileWriter(fn);
        exporter.exportGraph(graph, w);

    }

    public void solve(Gcosts<Node, Flow> graph) {

    }
}
