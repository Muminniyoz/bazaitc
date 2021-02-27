import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICoursePlan } from 'app/shared/model/course-plan.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CoursePlanService } from './course-plan.service';
import { CoursePlanDeleteDialogComponent } from './course-plan-delete-dialog.component';

@Component({
  selector: 'jhi-course-plan',
  templateUrl: './course-plan.component.html',
})
export class CoursePlanComponent implements OnInit, OnDestroy {
  coursePlans: ICoursePlan[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected coursePlanService: CoursePlanService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.coursePlans = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.coursePlanService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ICoursePlan[]>) => this.paginateCoursePlans(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.coursePlans = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCoursePlans();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICoursePlan): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCoursePlans(): void {
    this.eventSubscriber = this.eventManager.subscribe('coursePlanListModification', () => this.reset());
  }

  delete(coursePlan: ICoursePlan): void {
    const modalRef = this.modalService.open(CoursePlanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.coursePlan = coursePlan;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCoursePlans(data: ICoursePlan[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.coursePlans.push(data[i]);
      }
    }
  }
}
