package com.dmfa.train.filter;

import com.dmfa.train.graph.Path;

/**
 * This filter will check to see if the total weight of the given path is less
 * than the value specified on the constructor
 * 
 * @author "Jose Luis Ordiales Coscia <jlordiales@gmail.com>"
 * 
 * @param <V>
 *            The type of the nodes in the path
 */
public class WeightPathFilter<V> implements PathFilter<V> {
    private final int maxWeight;

    public WeightPathFilter(final int maxWeight) {
        super();
        this.maxWeight = maxWeight;
    }

    public boolean passFilter(final Path<V> path) {
        return path.getPathTotalWeight() < maxWeight;
    }

}
