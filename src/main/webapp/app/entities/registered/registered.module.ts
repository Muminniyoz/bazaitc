import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BazaitcSharedModule } from 'app/shared/shared.module';
import { RegisteredComponent } from './registered.component';
import { RegisteredDetailComponent } from './registered-detail.component';
import { RegisteredUpdateComponent } from './registered-update.component';
import { RegisteredDeleteDialogComponent } from './registered-delete-dialog.component';
import { registeredRoute } from './registered.route';

@NgModule({
  imports: [BazaitcSharedModule, RouterModule.forChild(registeredRoute)],
  declarations: [RegisteredComponent, RegisteredDetailComponent, RegisteredUpdateComponent, RegisteredDeleteDialogComponent],
  entryComponents: [RegisteredDeleteDialogComponent],
})
export class BazaitcRegisteredModule {}
