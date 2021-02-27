import { Month } from 'app/shared/model/enumerations/month.model';

export interface ICoursePlan {
  id?: number;
  month?: Month;
  technology?: string;
  extraPrice?: number;
}

export class CoursePlan implements ICoursePlan {
  constructor(public id?: number, public month?: Month, public technology?: string, public extraPrice?: number) {}
}
