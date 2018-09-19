package org.bakum.gcosts.util;

import org.bakum.gcosts.Costs;
import org.bakum.gcosts.Flow;
import org.bakum.gcosts.Gcosts;
import org.bakum.gcosts.Node;
import org.bakum.gcosts.enumeration.FlowDirection;

public class GraphPatterns {
    private static GraphPatterns instance;

    public static GraphPatterns getInstance() {
        if (instance == null) {
            instance = new GraphPatterns();
        }
        return instance;
    }

    private GraphPatterns() {
    }

    /**
     * Create new Gcosts graph for accounting (bookkiper) model
     *
     * @return new graph
     */
    public Gcosts<Node, Flow> getAccountingGraph() {
        //Создаем узлы графа
        Node p1 = new Node("p1", "Прямые затраты");
        Node n91 = new Node("91", "Общепроизводственные затраты");
        Node n231 = new Node("231", "Основное производство");
        Node n26 = new Node("26", "Готовая продукция");
        Node n901 = new Node("901", "Себестоимость реализованной продукции");

        //Создаем граф затрат
        Gcosts<Node, Flow> g = new Gcosts<Node, Flow>(Flow.class);

        //Добавляем узлы в граф затрат
        g.addVertex(p1);
        g.addVertex(n91);
        g.addVertex(n231);
        g.addVertex(n26);
        g.addVertex(n901);

        //Устанавливаем потоки затрат (ребра графа) между узлами, направление от источника к получателю
        //(источник Кт, получатель Дт)
        Flow fp1_231 = (Flow) g.addEdge(p1, n231); //Первичные затраты на себестоимость (прямые)
        fp1_231.setCost(new Costs(100.0, 10.0));
        g.setEdgeWeight(fp1_231, fp1_231.getAmount());

        Flow f91_231 = (Flow) g.addEdge(n91, n231); //Первичные затраты на себестоимость (ОПЗ)
        f91_231.setCost(new Costs(200.0, 1.0));
        g.setEdgeWeight(f91_231, f91_231.getAmount());
        Flow f231_231_begin = (Flow) g.addEdge(n231, n231); //Начальные остатки незавершенного производства
        f231_231_begin.setCost(new Costs(2.0, 10.0));
        f231_231_begin.setDirection(FlowDirection.BEGIN_VALUE);
        g.setEdgeWeight(f231_231_begin, f231_231_begin.getAmount());
        Flow f231_231_end = (Flow) g.addEdge(n231, n231); //Конечные остатки незавершенного производства
        f231_231_end.setCost(new Costs(0.0, -10.0));
        f231_231_end.setDirection(FlowDirection.END_VALUE);
        g.setEdgeWeight(f231_231_end, f231_231_end.getAmount());

        Flow f231_26 = (Flow) g.addEdge(n231, n26); //Выпуск продукции
        f231_26.setCost(new Costs(0.0, 10.0));
        g.setEdgeWeight(f231_26, f231_26.getAmount());

        Flow f26_901 = (Flow) g.addEdge(n26, n901); //Реализация продукции
        f26_901.setCost(new Costs(0.0, 10.0));
        g.setEdgeWeight(f26_901, f26_901.getAmount());
        return g;
    }

}
