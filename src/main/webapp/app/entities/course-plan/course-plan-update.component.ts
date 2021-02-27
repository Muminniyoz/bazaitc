import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICoursePlan, CoursePlan } from 'app/shared/model/course-plan.model';
import { CoursePlanService } from './course-plan.service';

@Component({
  selector: 'jhi-course-plan-update',
  templateUrl: './course-plan-update.component.html',
})
export class CoursePlanUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    month: [],
    technology: [],
    extraPrice: [],
  });

  constructor(protected coursePlanService: CoursePlanService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ coursePlan }) => {
      this.updateForm(coursePlan);
    });
  }

  updateForm(coursePlan: ICoursePlan): void {
    this.editForm.patchValue({
      id: coursePlan.id,
      month: coursePlan.month,
      technology: coursePlan.technology,
      extraPrice: coursePlan.extraPrice,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const coursePlan = this.createFromForm();
    if (coursePlan.id !== undefined) {
      this.subscribeToSaveResponse(this.coursePlanService.update(coursePlan));
    } else {
      this.subscribeToSaveResponse(this.coursePlanService.create(coursePlan));
    }
  }

  private createFromForm(): ICoursePlan {
    return {
      ...new CoursePlan(),
      id: this.editForm.get(['id'])!.value,
      month: this.editForm.get(['month'])!.value,
      technology: this.editForm.get(['technology'])!.value,
      extraPrice: this.editForm.get(['extraPrice'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICoursePlan>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
