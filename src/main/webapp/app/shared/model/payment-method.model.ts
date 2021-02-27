export interface IPaymentMethod {
  id?: number;
  paymentMethod?: string;
  description?: string;
  active?: boolean;
}

export class PaymentMethod implements IPaymentMethod {
  constructor(public id?: number, public paymentMethod?: string, public description?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
