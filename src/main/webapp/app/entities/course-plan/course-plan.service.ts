import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICoursePlan } from 'app/shared/model/course-plan.model';

type EntityResponseType = HttpResponse<ICoursePlan>;
type EntityArrayResponseType = HttpResponse<ICoursePlan[]>;

@Injectable({ providedIn: 'root' })
export class CoursePlanService {
  public resourceUrl = SERVER_API_URL + 'api/course-plans';

  constructor(protected http: HttpClient) {}

  create(coursePlan: ICoursePlan): Observable<EntityResponseType> {
    return this.http.post<ICoursePlan>(this.resourceUrl, coursePlan, { observe: 'response' });
  }

  update(coursePlan: ICoursePlan): Observable<EntityResponseType> {
    return this.http.put<ICoursePlan>(this.resourceUrl, coursePlan, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICoursePlan>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICoursePlan[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
