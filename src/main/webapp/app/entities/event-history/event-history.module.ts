import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BazaitcSharedModule } from 'app/shared/shared.module';
import { EventHistoryComponent } from './event-history.component';
import { EventHistoryDetailComponent } from './event-history-detail.component';
import { EventHistoryUpdateComponent } from './event-history-update.component';
import { EventHistoryDeleteDialogComponent } from './event-history-delete-dialog.component';
import { eventHistoryRoute } from './event-history.route';

@NgModule({
  imports: [BazaitcSharedModule, RouterModule.forChild(eventHistoryRoute)],
  declarations: [EventHistoryComponent, EventHistoryDetailComponent, EventHistoryUpdateComponent, EventHistoryDeleteDialogComponent],
  entryComponents: [EventHistoryDeleteDialogComponent],
})
export class BazaitcEventHistoryModule {}
