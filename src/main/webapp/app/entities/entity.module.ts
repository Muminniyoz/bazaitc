import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'regions',
        loadChildren: () => import('./regions/regions.module').then(m => m.BazaitcRegionsModule),
      },
      {
        path: 'center',
        loadChildren: () => import('./center/center.module').then(m => m.BazaitcCenterModule),
      },
      {
        path: 'skill',
        loadChildren: () => import('./skill/skill.module').then(m => m.BazaitcSkillModule),
      },
      {
        path: 'teacher',
        loadChildren: () => import('./teacher/teacher.module').then(m => m.BazaitcTeacherModule),
      },
      {
        path: 'course',
        loadChildren: () => import('./course/course.module').then(m => m.BazaitcCourseModule),
      },
      {
        path: 'registered',
        loadChildren: () => import('./registered/registered.module').then(m => m.BazaitcRegisteredModule),
      },
      {
        path: 'student',
        loadChildren: () => import('./student/student.module').then(m => m.BazaitcStudentModule),
      },
      {
        path: 'participant',
        loadChildren: () => import('./participant/participant.module').then(m => m.BazaitcParticipantModule),
      },
      {
        path: 'payment-method',
        loadChildren: () => import('./payment-method/payment-method.module').then(m => m.BazaitcPaymentMethodModule),
      },
      {
        path: 'system-config',
        loadChildren: () => import('./system-config/system-config.module').then(m => m.BazaitcSystemConfigModule),
      },
      {
        path: 'payment',
        loadChildren: () => import('./payment/payment.module').then(m => m.BazaitcPaymentModule),
      },
      {
        path: 'event-history',
        loadChildren: () => import('./event-history/event-history.module').then(m => m.BazaitcEventHistoryModule),
      },
      {
        path: 'course-plan',
        loadChildren: () => import('./course-plan/course-plan.module').then(m => m.BazaitcCoursePlanModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class BazaitcEntityModule {}
