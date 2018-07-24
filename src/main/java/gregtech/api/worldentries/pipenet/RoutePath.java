package gregtech.api.worldentries.pipenet;

import java.util.stream.Collector;

public class RoutePath<P, A, R> extends NodeChain<P> {
    PipeNet.LinkedNode<P> endNode = null;
    private final Collector<PipeNet.LinkedNode<P>, A, R> collector;
    private A accumulator;
    public RoutePath(Collector<PipeNet.LinkedNode<P>, A, R> collector) {
        this.collector = collector;
        this.accumulator = collector.supplier().get();
    }

    public RoutePath(PipeNet.LinkedNode<P> startNode, Collector<PipeNet.LinkedNode<P>, A, R> collector) {
        super(startNode);
        this.collector = collector;
        this.accumulator = collector.supplier().get();
        if (startNode != null) {
            for (endNode = startNode; endNode.target != null; endNode = endNode.target) {
                collector.accumulator().accept(accumulator, endNode);
            }
            collector.accumulator().accept(accumulator, endNode);
        }
    }

    @Override
    public RoutePath<P, A, R> extend(PipeNet.Node<P> node) {
        PipeNet.LinkedNode<P> newNode = new PipeNet.LinkedNode<>(node, startNode);
        startNode = newNode;
        if (endNode == null) endNode = newNode;
        collector.accumulator().accept(accumulator, newNode);
        return this;
    }

    public RoutePath<P, A, R> extend(RoutePath<P, A, R> routePath) {
        accumulator = collector.combiner().apply(accumulator, routePath.accumulator);
        routePath.endNode.target = startNode;
        startNode = routePath.startNode;
        return this;
    }

    public PipeNet.LinkedNode<P> getEndNode() {
        return endNode;
    }

    public R getAccumulatedVal() {
        return collector.finisher().apply(accumulator);
    }
}
