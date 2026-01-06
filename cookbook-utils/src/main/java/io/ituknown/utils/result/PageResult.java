package io.ituknown.utils.result;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class PageResult<T> extends Result<Collection<T>> {
    private int page;
    private int pageSize;
    private int totalRecords;

    public PageResult() {
        super(new ArrayList<>());
    }

    public PageResult(Collection<T> collection) {
        super(collection);
    }

    public void add(T data) {
        if (Objects.nonNull(data)) {
            getData().add(data);
        }
    }

    public void addAll(List<T> dataList) {
        if (Objects.nonNull(dataList) && !dataList.isEmpty()) {
            getData().addAll(dataList);
        }
    }
}