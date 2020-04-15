import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibrarySharedModule } from 'app/shared/shared.module';
import { NotaFiscalComponent } from './nota-fiscal.component';
import { NotaFiscalDetailComponent } from './nota-fiscal-detail.component';
import { NotaFiscalUpdateComponent } from './nota-fiscal-update.component';
import { NotaFiscalDeleteDialogComponent } from './nota-fiscal-delete-dialog.component';
import { notaFiscalRoute } from './nota-fiscal.route';

@NgModule({
  imports: [LibrarySharedModule, RouterModule.forChild(notaFiscalRoute)],
  declarations: [NotaFiscalComponent, NotaFiscalDetailComponent, NotaFiscalUpdateComponent, NotaFiscalDeleteDialogComponent],
  entryComponents: [NotaFiscalDeleteDialogComponent]
})
export class LibraryNotaFiscalModule {}
