package com.alibaba.csp.sentinel.dashboard.repository.jpa.specfication;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.function.Function;

@SuperBuilder
@NoArgsConstructor
public abstract class BaseSpecification<T> implements Specification<T> {
  private static final Specification<?> EMPTY = (root, criteriaQuery, criteriaBuilder) -> null;

  protected Specification<T> empty() {
    return (Specification<T>) EMPTY;
  }

  protected Specification<T> equal(String column, Object value) {
    if (value == null) {
      return empty();
    }
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(column), value);
  }

  protected Specification<T> notEqual(String column, Object value) {
    if (value == null) {
      return empty();
    }
    return (root, criteriaQuery, criteriaBuilder) ->
        criteriaBuilder.notEqual(root.get(column), value);
  }

  protected Specification<T> equalString(String column, String value) {
    if (value == null) {
      return empty();
    }

    return (root, criteriaQuery, criteriaBuilder) ->
        criteriaBuilder.equal(root.get(column).as(String.class), value);
  }

  protected Specification<T> in(String column, List<String> values) {
    if (CollectionUtils.isEmpty(values)) {
      return empty();
    }

    return (root, criteriaQuery, criteriaBuilder) -> {
      CriteriaBuilder.In<String> p = criteriaBuilder.in(root.get(column).as(String.class));
      for (String value : values) {
        p.value(value);
      }

      return p;
    };
  }

  protected <R> Specification<T> inWithStr(
      String column, List<R> values, Function<R, String> func) {
    if (CollectionUtils.isEmpty(values)) {
      return empty();
    }
    return (root, criteriaQuery, criteriaBuilder) -> {
      CriteriaBuilder.In<String> p = criteriaBuilder.in(root.get(column).as(String.class));
      if (func == null) {
        for (R value : values) {
          p.value(value.toString());
        }
      } else {
        for (R value : values) {
          p.value(func.apply(value));
        }
      }

      return p;
    };
  }

  private <O, V> Specification<T> in(String column, List<O> values, Function<O, V> function) {
    if (CollectionUtils.isEmpty(values)) {
      return empty();
    }

    return (root, criteriaQuery, criteriaBuilder) -> {
      CriteriaBuilder.In<V> p = criteriaBuilder.in(root.get(column));
      for (O value : values) {
        p.value(function.apply(value));
      }

      return p;
    };
  }

  protected Specification<T> gt(String column, Number value) {
    if (value == null) {
      return empty();
    }

    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.gt(root.get(column), value);
  }

  protected Specification<T> ge(String column, Number value) {
    if (value == null) {
      return empty();
    }

    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.ge(root.get(column), value);
  }

  protected Specification<T> lt(String column, Number value) {
    if (value == null) {
      return empty();
    }

    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lt(root.get(column), value);
  }

  protected Specification<T> le(String column, Number value) {
    if (value == null) {
      return empty();
    }

    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.le(root.get(column), value);
  }

  protected <Y> Specification<T> contains(String column, Y value) {
    if (value == null) {
      return empty();
    }
    return (root, criteriaQuery, cb) -> {
      Expression<String> exp =
          cb.function("string_to_array", String.class, cb.literal(value), cb.literal(","));
      return cb.isTrue(cb.function("arraycontains", Boolean.class, root.get(column), exp));
    };
  }

  protected <Y extends Comparable<? super Y>> Specification<T> greaterThan(String column, Y value) {
    if (value == null) {
      return empty();
    }

    return (root, criteriaQuery, criteriaBuilder) ->
        criteriaBuilder.greaterThan(root.get(column), value);
  }

  protected <Y extends Comparable<? super Y>> Specification<T> greaterThanOrEqualTo(
      String column, Y value) {
    if (value == null) {
      return empty();
    }

    return (root, criteriaQuery, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(root.get(column), value);
  }

  protected <Y extends Comparable<? super Y>> Specification<T> lessThan(String column, Y value) {
    if (value == null) {
      return empty();
    }

    return (root, criteriaQuery, criteriaBuilder) ->
        criteriaBuilder.lessThan(root.get(column), value);
  }

  protected <Y extends Comparable<? super Y>> Specification<T> lessThanOrEqualTo(
      String column, Y value) {
    if (value == null) {
      return empty();
    }

    return (root, criteriaQuery, criteriaBuilder) ->
        criteriaBuilder.lessThanOrEqualTo(root.get(column), value);
  }

  @Override
  public abstract Predicate toPredicate(
      Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
}
