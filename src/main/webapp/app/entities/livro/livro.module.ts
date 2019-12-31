import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LibrarySharedModule } from 'app/shared/shared.module';
import { LivroComponent } from './livro.component';
import { LivroDetailComponent } from './livro-detail.component';
import { LivroUpdateComponent } from './livro-update.component';
import { LivroDeleteDialogComponent } from './livro-delete-dialog.component';
import { livroRoute } from './livro.route';

@NgModule({
  imports: [LibrarySharedModule, RouterModule.forChild(livroRoute)],
  declarations: [LivroComponent, LivroDetailComponent, LivroUpdateComponent, LivroDeleteDialogComponent],
  entryComponents: [LivroDeleteDialogComponent]
})
export class LibraryLivroModule {}
