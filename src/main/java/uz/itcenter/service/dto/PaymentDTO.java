package uz.itcenter.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import uz.itcenter.domain.enumeration.PaymentStatus;
import uz.itcenter.domain.enumeration.Month;

/**
 * A DTO for the {@link uz.itcenter.domain.Payment} entity.
 */
public class PaymentDTO implements Serializable {
    
    private Long id;

    private ZonedDateTime paymentDate;

    private String paymentProvider;

    private BigDecimal amount;

    private PaymentStatus paymentStatus;

    private String curency;

    private String customerName;

    private Boolean isEnough;

    private Boolean isConfirmed;

    private Month month;

    private String info;


    private Long modifiedById;

    private Long confirmedById;

    private Long studentId;

    private Long methodId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(ZonedDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentProvider() {
        return paymentProvider;
    }

    public void setPaymentProvider(String paymentProvider) {
        this.paymentProvider = paymentProvider;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCurency() {
        return curency;
    }

    public void setCurency(String curency) {
        this.curency = curency;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Boolean isIsEnough() {
        return isEnough;
    }

    public void setIsEnough(Boolean isEnough) {
        this.isEnough = isEnough;
    }

    public Boolean isIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getModifiedById() {
        return modifiedById;
    }

    public void setModifiedById(Long userId) {
        this.modifiedById = userId;
    }

    public Long getConfirmedById() {
        return confirmedById;
    }

    public void setConfirmedById(Long userId) {
        this.confirmedById = userId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long participantId) {
        this.studentId = participantId;
    }

    public Long getMethodId() {
        return methodId;
    }

    public void setMethodId(Long paymentMethodId) {
        this.methodId = paymentMethodId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDTO)) {
            return false;
        }

        return id != null && id.equals(((PaymentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDTO{" +
            "id=" + getId() +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", paymentProvider='" + getPaymentProvider() + "'" +
            ", amount=" + getAmount() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", curency='" + getCurency() + "'" +
            ", customerName='" + getCustomerName() + "'" +
            ", isEnough='" + isIsEnough() + "'" +
            ", isConfirmed='" + isIsConfirmed() + "'" +
            ", month='" + getMonth() + "'" +
            ", info='" + getInfo() + "'" +
            ", modifiedById=" + getModifiedById() +
            ", confirmedById=" + getConfirmedById() +
            ", studentId=" + getStudentId() +
            ", methodId=" + getMethodId() +
            "}";
    }
}
