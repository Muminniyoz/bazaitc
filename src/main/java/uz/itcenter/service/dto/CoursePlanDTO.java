package uz.itcenter.service.dto;

import java.io.Serializable;
import uz.itcenter.domain.enumeration.Month;

/**
 * A DTO for the {@link uz.itcenter.domain.CoursePlan} entity.
 */
public class CoursePlanDTO implements Serializable {
    
    private Long id;

    private Month month;

    private String technology;

    private Integer extraPrice;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public Integer getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(Integer extraPrice) {
        this.extraPrice = extraPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoursePlanDTO)) {
            return false;
        }

        return id != null && id.equals(((CoursePlanDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CoursePlanDTO{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", technology='" + getTechnology() + "'" +
            ", extraPrice=" + getExtraPrice() +
            "}";
    }
}
