package com.dmfa.train.filter;

import com.dmfa.train.filter.composite.AndPathFilter;
import com.dmfa.train.filter.composite.NotPathFilter;
import com.dmfa.train.filter.composite.OrPathFilter;
import com.dmfa.train.graph.Path;

/**
 * Base interface to filter paths given different criteria. Each concrete
 * implementation can decide when the given path is valid or not. The filters in
 * this package use the Composite design pattern to easily extend and combine an
 * arbitrary number of filters.
 * 
 * @author "Jose Luis Ordiales Coscia <jlordiales@gmail.com>"
 * 
 * @param <V>
 *            The type of the nodes in the path
 * @see AndPathFilter
 * @see OrPathFilter
 * @see NotPathFilter
 */
public interface PathFilter<V> {
    boolean passFilter(final Path<V> path);

}
