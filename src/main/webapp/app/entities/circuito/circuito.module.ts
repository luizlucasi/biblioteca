import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibrarySharedModule } from 'app/shared/shared.module';
import { CircuitoComponent } from './circuito.component';
import { CircuitoDetailComponent } from './circuito-detail.component';
import { CircuitoUpdateComponent } from './circuito-update.component';
import { CircuitoDeleteDialogComponent } from './circuito-delete-dialog.component';
import { circuitoRoute } from './circuito.route';

@NgModule({
  imports: [LibrarySharedModule, RouterModule.forChild(circuitoRoute)],
  declarations: [CircuitoComponent, CircuitoDetailComponent, CircuitoUpdateComponent, CircuitoDeleteDialogComponent],
  entryComponents: [CircuitoDeleteDialogComponent]
})
export class LibraryCircuitoModule {}
