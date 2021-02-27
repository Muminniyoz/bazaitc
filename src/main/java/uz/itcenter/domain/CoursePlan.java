package uz.itcenter.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import uz.itcenter.domain.enumeration.Month;

/**
 * A CoursePlan.
 */
@Entity
@Table(name = "course_plan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CoursePlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "month")
    private Month month;

    @Column(name = "technology")
    private String technology;

    @Column(name = "extra_price")
    private Integer extraPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Month getMonth() {
        return month;
    }

    public CoursePlan month(Month month) {
        this.month = month;
        return this;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public String getTechnology() {
        return technology;
    }

    public CoursePlan technology(String technology) {
        this.technology = technology;
        return this;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public Integer getExtraPrice() {
        return extraPrice;
    }

    public CoursePlan extraPrice(Integer extraPrice) {
        this.extraPrice = extraPrice;
        return this;
    }

    public void setExtraPrice(Integer extraPrice) {
        this.extraPrice = extraPrice;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoursePlan)) {
            return false;
        }
        return id != null && id.equals(((CoursePlan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CoursePlan{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", technology='" + getTechnology() + "'" +
            ", extraPrice=" + getExtraPrice() +
            "}";
    }
}
