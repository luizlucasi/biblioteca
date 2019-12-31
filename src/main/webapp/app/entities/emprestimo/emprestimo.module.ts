import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibrarySharedModule } from 'app/shared/shared.module';
import { EmprestimoComponent } from './emprestimo.component';
import { EmprestimoDetailComponent } from './emprestimo-detail.component';
import { EmprestimoUpdateComponent } from './emprestimo-update.component';
import { EmprestimoDeleteDialogComponent } from './emprestimo-delete-dialog.component';
import { emprestimoRoute } from './emprestimo.route';

@NgModule({
  imports: [LibrarySharedModule, RouterModule.forChild(emprestimoRoute)],
  declarations: [EmprestimoComponent, EmprestimoDetailComponent, EmprestimoUpdateComponent, EmprestimoDeleteDialogComponent],
  entryComponents: [EmprestimoDeleteDialogComponent]
})
export class LibraryEmprestimoModule {}
