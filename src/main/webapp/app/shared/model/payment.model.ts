import { Moment } from 'moment';
import { PaymentStatus } from 'app/shared/model/enumerations/payment-status.model';
import { Month } from 'app/shared/model/enumerations/month.model';

export interface IPayment {
  id?: number;
  paymentDate?: Moment;
  paymentProvider?: string;
  amount?: number;
  paymentStatus?: PaymentStatus;
  curency?: string;
  customerName?: string;
  isEnough?: boolean;
  isConfirmed?: boolean;
  month?: Month;
  info?: string;
  modifiedById?: number;
  confirmedById?: number;
  studentId?: number;
  methodId?: number;
}

export class Payment implements IPayment {
  constructor(
    public id?: number,
    public paymentDate?: Moment,
    public paymentProvider?: string,
    public amount?: number,
    public paymentStatus?: PaymentStatus,
    public curency?: string,
    public customerName?: string,
    public isEnough?: boolean,
    public isConfirmed?: boolean,
    public month?: Month,
    public info?: string,
    public modifiedById?: number,
    public confirmedById?: number,
    public studentId?: number,
    public methodId?: number
  ) {
    this.isEnough = this.isEnough || false;
    this.isConfirmed = this.isConfirmed || false;
  }
}
