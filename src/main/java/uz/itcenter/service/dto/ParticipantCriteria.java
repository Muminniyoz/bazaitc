package uz.itcenter.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import uz.itcenter.domain.enumeration.ParticipantStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link uz.itcenter.domain.Participant} entity. This class is used
 * in {@link uz.itcenter.web.rest.ParticipantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /participants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParticipantCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ParticipantStatus
     */
    public static class ParticipantStatusFilter extends Filter<ParticipantStatus> {

        public ParticipantStatusFilter() {
        }

        public ParticipantStatusFilter(ParticipantStatusFilter filter) {
            super(filter);
        }

        @Override
        public ParticipantStatusFilter copy() {
            return new ParticipantStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter startingDate;

    private BooleanFilter active;

    private ParticipantStatusFilter status;

    private StringFilter contractNumber;

    private StringFilter info;

    private LongFilter studentId;

    private LongFilter courseId;

    public ParticipantCriteria() {
    }

    public ParticipantCriteria(ParticipantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.startingDate = other.startingDate == null ? null : other.startingDate.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.contractNumber = other.contractNumber == null ? null : other.contractNumber.copy();
        this.info = other.info == null ? null : other.info.copy();
        this.studentId = other.studentId == null ? null : other.studentId.copy();
        this.courseId = other.courseId == null ? null : other.courseId.copy();
    }

    @Override
    public ParticipantCriteria copy() {
        return new ParticipantCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDateFilter startingDate) {
        this.startingDate = startingDate;
    }

    public BooleanFilter getActive() {
        return active;
    }

    public void setActive(BooleanFilter active) {
        this.active = active;
    }

    public ParticipantStatusFilter getStatus() {
        return status;
    }

    public void setStatus(ParticipantStatusFilter status) {
        this.status = status;
    }

    public StringFilter getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(StringFilter contractNumber) {
        this.contractNumber = contractNumber;
    }

    public StringFilter getInfo() {
        return info;
    }

    public void setInfo(StringFilter info) {
        this.info = info;
    }

    public LongFilter getStudentId() {
        return studentId;
    }

    public void setStudentId(LongFilter studentId) {
        this.studentId = studentId;
    }

    public LongFilter getCourseId() {
        return courseId;
    }

    public void setCourseId(LongFilter courseId) {
        this.courseId = courseId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ParticipantCriteria that = (ParticipantCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(startingDate, that.startingDate) &&
            Objects.equals(active, that.active) &&
            Objects.equals(status, that.status) &&
            Objects.equals(contractNumber, that.contractNumber) &&
            Objects.equals(info, that.info) &&
            Objects.equals(studentId, that.studentId) &&
            Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        startingDate,
        active,
        status,
        contractNumber,
        info,
        studentId,
        courseId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParticipantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startingDate != null ? "startingDate=" + startingDate + ", " : "") +
                (active != null ? "active=" + active + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (contractNumber != null ? "contractNumber=" + contractNumber + ", " : "") +
                (info != null ? "info=" + info + ", " : "") +
                (studentId != null ? "studentId=" + studentId + ", " : "") +
                (courseId != null ? "courseId=" + courseId + ", " : "") +
            "}";
    }

}
