package gregtech.api.worldentries.pipenet;

import java.util.Iterator;

public class NodeChain<P> implements Iterable<PipeNet.Node<P>> {
    PipeNet.LinkedNode<P> startNode = null;
    public NodeChain(){}
    public NodeChain(PipeNet.LinkedNode<P> startNode) {
        this.startNode = startNode;
    }

    public NodeChain<P> extend(PipeNet.Node<P> node) {
        PipeNet.LinkedNode<P> newNode = new PipeNet.LinkedNode<>(node, startNode);
        return this;
    }

    public PipeNet.LinkedNode<P> getStartNode() {
        return startNode;
    }

    @Override
    public Iterator<PipeNet.Node<P>> iterator() {
        return new NodeChainIterator<>(this);
    }

    static class NodeChainIterator<P> implements Iterator<PipeNet.Node<P>> {
        private NodeChain<P> chain;
        private PipeNet.LinkedNode<P> current = null;
        private NodeChainIterator(NodeChain<P> chain) {
            this.chain = chain;
        }

        @Override
        public PipeNet.LinkedNode<P> next() {
            return current = current == null ? chain.startNode : current.target;
        }

        @Override
        public boolean hasNext() {
            return (current == null ? chain.startNode : current.target) != null;
        }
    }

}
