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
      },
      {
        path: 'nota-fiscal',
        loadChildren: () => import('./nota-fiscal/nota-fiscal.module').then(m => m.LibraryNotaFiscalModule)
      },
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.LibraryClienteModule)
      },
      {
        path: 'contrato',
        loadChildren: () => import('./contrato/contrato.module').then(m => m.LibraryContratoModule)
      },
      {
        path: 'circuito',
        loadChildren: () => import('./circuito/circuito.module').then(m => m.LibraryCircuitoModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class LibraryEntityModule {}
