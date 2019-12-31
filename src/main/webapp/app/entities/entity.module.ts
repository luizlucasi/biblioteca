import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'autor',
        loadChildren: () => import('./autor/autor.module').then(m => m.LibraryAutorModule)
      },
      {
        path: 'aluno',
        loadChildren: () => import('./aluno/aluno.module').then(m => m.LibraryAlunoModule)
      },
      {
        path: 'livro',
        loadChildren: () => import('./livro/livro.module').then(m => m.LibraryLivroModule)
      },
      {
        path: 'emprestimo',
        loadChildren: () => import('./emprestimo/emprestimo.module').then(m => m.LibraryEmprestimoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class LibraryEntityModule {}
