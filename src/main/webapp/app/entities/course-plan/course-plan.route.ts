import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICoursePlan, CoursePlan } from 'app/shared/model/course-plan.model';
import { CoursePlanService } from './course-plan.service';
import { CoursePlanComponent } from './course-plan.component';
import { CoursePlanDetailComponent } from './course-plan-detail.component';
import { CoursePlanUpdateComponent } from './course-plan-update.component';

@Injectable({ providedIn: 'root' })
export class CoursePlanResolve implements Resolve<ICoursePlan> {
  constructor(private service: CoursePlanService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICoursePlan> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((coursePlan: HttpResponse<CoursePlan>) => {
          if (coursePlan.body) {
            return of(coursePlan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CoursePlan());
  }
}

export const coursePlanRoute: Routes = [
  {
    path: '',
    component: CoursePlanComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazaitcApp.coursePlan.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CoursePlanDetailComponent,
    resolve: {
      coursePlan: CoursePlanResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazaitcApp.coursePlan.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CoursePlanUpdateComponent,
    resolve: {
      coursePlan: CoursePlanResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazaitcApp.coursePlan.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CoursePlanUpdateComponent,
    resolve: {
      coursePlan: CoursePlanResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'bazaitcApp.coursePlan.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
