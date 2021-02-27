import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICoursePlan } from 'app/shared/model/course-plan.model';
import { CoursePlanService } from './course-plan.service';

@Component({
  templateUrl: './course-plan-delete-dialog.component.html',
})
export class CoursePlanDeleteDialogComponent {
  coursePlan?: ICoursePlan;

  constructor(
    protected coursePlanService: CoursePlanService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.coursePlanService.delete(id).subscribe(() => {
      this.eventManager.broadcast('coursePlanListModification');
      this.activeModal.close();
    });
  }
}
