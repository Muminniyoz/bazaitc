import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BazaitcSharedModule } from 'app/shared/shared.module';
import { CoursePlanComponent } from './course-plan.component';
import { CoursePlanDetailComponent } from './course-plan-detail.component';
import { CoursePlanUpdateComponent } from './course-plan-update.component';
import { CoursePlanDeleteDialogComponent } from './course-plan-delete-dialog.component';
import { coursePlanRoute } from './course-plan.route';

@NgModule({
  imports: [BazaitcSharedModule, RouterModule.forChild(coursePlanRoute)],
  declarations: [CoursePlanComponent, CoursePlanDetailComponent, CoursePlanUpdateComponent, CoursePlanDeleteDialogComponent],
  entryComponents: [CoursePlanDeleteDialogComponent],
})
export class BazaitcCoursePlanModule {}
