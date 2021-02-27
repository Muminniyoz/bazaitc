package uz.itcenter.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import uz.itcenter.domain.enumeration.Month;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link uz.itcenter.domain.CoursePlan} entity. This class is used
 * in {@link uz.itcenter.web.rest.CoursePlanResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /course-plans?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CoursePlanCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Month
     */
    public static class MonthFilter extends Filter<Month> {

        public MonthFilter() {
        }

        public MonthFilter(MonthFilter filter) {
            super(filter);
        }

        @Override
        public MonthFilter copy() {
            return new MonthFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private MonthFilter month;

    private StringFilter technology;

    private IntegerFilter extraPrice;

    public CoursePlanCriteria() {
    }

    public CoursePlanCriteria(CoursePlanCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.month = other.month == null ? null : other.month.copy();
        this.technology = other.technology == null ? null : other.technology.copy();
        this.extraPrice = other.extraPrice == null ? null : other.extraPrice.copy();
    }

    @Override
    public CoursePlanCriteria copy() {
        return new CoursePlanCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public MonthFilter getMonth() {
        return month;
    }

    public void setMonth(MonthFilter month) {
        this.month = month;
    }

    public StringFilter getTechnology() {
        return technology;
    }

    public void setTechnology(StringFilter technology) {
        this.technology = technology;
    }

    public IntegerFilter getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(IntegerFilter extraPrice) {
        this.extraPrice = extraPrice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CoursePlanCriteria that = (CoursePlanCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(month, that.month) &&
            Objects.equals(technology, that.technology) &&
            Objects.equals(extraPrice, that.extraPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        month,
        technology,
        extraPrice
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CoursePlanCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (month != null ? "month=" + month + ", " : "") +
                (technology != null ? "technology=" + technology + ", " : "") +
                (extraPrice != null ? "extraPrice=" + extraPrice + ", " : "") +
            "}";
    }

}
