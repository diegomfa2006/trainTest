package com.dmfa.train.filter.composite;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.dmfa.train.filter.ExactHopsPathFilter;
import com.dmfa.train.filter.PathFilter;
import com.dmfa.train.graph.DefaultEdge;
import com.dmfa.train.graph.GraphPath;
import com.dmfa.train.graph.Path;

public class NotPathFilterTest {
    private final PathFilter<String> notFilter = new NotPathFilter<String>(new ExactHopsPathFilter<String>(3));
    private Path<String> targetPath;

    @Before
    public void initPath() {
        targetPath = GraphPath.emptyPath();
        targetPath.addEdge(DefaultEdge.getWeightedEdge("A", "B", 5));
        targetPath.addEdge(DefaultEdge.getWeightedEdge("B", "C", 2));
    }

    @Test
    public void shouldFailWhenFilterReturnsTrue() {
        targetPath.addEdge(DefaultEdge.getWeightedEdge("C", "D", 2));
        // The path has 3 hops now so the original filter would return true
        // and the NotFilter should return false
        assertThat(notFilter.passFilter(targetPath)).isFalse();
    }

    @Test
    public void shouldPassWhenFilterReturnsFalse() {
        // The path has 2 hops so the original filter would return false
        // and the NotFilter should return true
        assertThat(notFilter.passFilter(targetPath)).isTrue();
    }

}
