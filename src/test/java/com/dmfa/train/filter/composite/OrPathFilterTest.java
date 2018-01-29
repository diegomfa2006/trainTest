package com.dmfa.train.filter.composite;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.dmfa.train.filter.ExactHopsPathFilter;
import com.dmfa.train.filter.PathFilter;
import com.dmfa.train.filter.WeightPathFilter;
import com.dmfa.train.graph.DefaultEdge;
import com.dmfa.train.graph.GraphPath;
import com.dmfa.train.graph.Path;

public class OrPathFilterTest {
    private final PathFilter<String> filter = new OrPathFilter<String>(new ExactHopsPathFilter<String>(3),
            new WeightPathFilter<String>(15));
    private Path<String> targetPath;

    @Before
    public void initPath() {
        targetPath = GraphPath.emptyPath();
        targetPath.addEdge(DefaultEdge.getWeightedEdge("A", "B", 5));
        targetPath.addEdge(DefaultEdge.getWeightedEdge("B", "C", 2));
    }

    @Test
    public void shouldFailWhenBothFilterFail() {
        targetPath.addEdge(DefaultEdge.getWeightedEdge("C", "D", 30));
        targetPath.addEdge(DefaultEdge.getWeightedEdge("D", "E", 3));
        // The filter checks to see if the path has exactly 3 hops or if its
        // weight is less than 15, so the previous path should fail
        assertThat(filter.passFilter(targetPath)).isFalse();
    }

    @Test
    public void shouldPassWhenOnlySecondFilterFails() {
        targetPath.addEdge(DefaultEdge.getWeightedEdge("C", "D", 30));
        // The path has now 3 hops and a weight of 37
        // so the first condition is valid and the second one not
        assertThat(filter.passFilter(targetPath)).isTrue();
    }

    @Test
    public void shouldPassWhenOnlyFirstFilterFails() {
        // The path has 2 hops and a weight of 7
        // so the first condition is not valid but the second one is
        assertThat(filter.passFilter(targetPath)).isTrue();
    }

}
