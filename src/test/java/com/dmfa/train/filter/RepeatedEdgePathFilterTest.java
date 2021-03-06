package com.dmfa.train.filter;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import com.dmfa.train.graph.DefaultEdge;
import com.dmfa.train.graph.GraphPath;
import com.dmfa.train.graph.Path;

public class RepeatedEdgePathFilterTest {
    private final PathFilter<String> filter = new RepeatedEdgePathFilter<String>();

    @Test
    public void shouldPassWhenPathHasNoRepeatedEdges() {
        final Path<String> targetPath = GraphPath.emptyPath();
        targetPath.addEdge(DefaultEdge.getWeightedEdge("A", "B", 5));
        targetPath.addEdge(DefaultEdge.getWeightedEdge("B", "C", 15));
        targetPath.addEdge(DefaultEdge.getWeightedEdge("C", "D", 25));
        assertThat(filter.passFilter(targetPath)).isTrue();
    }

    @Test
    public void shouldFailWhenPathHasRepeatedEdges() {
        final Path<String> targetPath = GraphPath.emptyPath();
        targetPath.addEdge(DefaultEdge.getWeightedEdge("A", "B", 5));
        targetPath.addEdge(DefaultEdge.getWeightedEdge("B", "C", 15));
        targetPath.addEdge(DefaultEdge.getWeightedEdge("C", "D", 25));
        targetPath.addEdge(DefaultEdge.getWeightedEdge("D", "A", 15));
        targetPath.addEdge(DefaultEdge.getWeightedEdge("A", "B", 5));
        assertThat(filter.passFilter(targetPath)).isFalse();
    }

}
