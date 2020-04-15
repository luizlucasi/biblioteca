import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LibraryTestModule } from '../../../test.module';
import { NotaFiscalDetailComponent } from 'app/entities/nota-fiscal/nota-fiscal-detail.component';
import { NotaFiscal } from 'app/shared/model/nota-fiscal.model';

describe('Component Tests', () => {
  describe('NotaFiscal Management Detail Component', () => {
    let comp: NotaFiscalDetailComponent;
    let fixture: ComponentFixture<NotaFiscalDetailComponent>;
    const route = ({ data: of({ notaFiscal: new NotaFiscal(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibraryTestModule],
        declarations: [NotaFiscalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NotaFiscalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NotaFiscalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load notaFiscal on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.notaFiscal).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
