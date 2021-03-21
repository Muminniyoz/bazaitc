import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { BazaitcSharedModule } from 'app/shared/shared.module';
import { BazaitcCoreModule } from 'app/core/core.module';
import { BazaitcAppRoutingModule } from './app-routing.module';
import { BazaitcHomeModule } from './home/home.module';
import { BazaitcEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { ManagerComponent } from './src/main/webapp/app/pages/manager/manager.component';
import { TeacherComponent } from './pages/teacher/teacher.component';
import { DirectorComponent } from './pages/director/director.component';
import { AccontantComponent } from './pages/accontant/accontant.component';
import { AccountantComponent } from './pages/accountant/accountant.component';

@NgModule({
  imports: [
    BrowserModule,
    BazaitcSharedModule,
    BazaitcCoreModule,
    BazaitcHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    BazaitcEntityModule,
    BazaitcAppRoutingModule,
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    ManagerComponent,
    TeacherComponent,
    DirectorComponent,
    AccontantComponent,
    AccountantComponent,
  ],
  bootstrap: [MainComponent],
})
export class BazaitcAppModule {}
