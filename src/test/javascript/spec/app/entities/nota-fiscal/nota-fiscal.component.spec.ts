import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LibraryTestModule } from '../../../test.module';
import { NotaFiscalComponent } from 'app/entities/nota-fiscal/nota-fiscal.component';
import { NotaFiscalService } from 'app/entities/nota-fiscal/nota-fiscal.service';
import { NotaFiscal } from 'app/shared/model/nota-fiscal.model';

describe('Component Tests', () => {
  describe('NotaFiscal Management Component', () => {
    let comp: NotaFiscalComponent;
    let fixture: ComponentFixture<NotaFiscalComponent>;
    let service: NotaFiscalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibraryTestModule],
        declarations: [NotaFiscalComponent]
      })
        .overrideTemplate(NotaFiscalComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NotaFiscalComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NotaFiscalService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new NotaFiscal(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.notaFiscals && comp.notaFiscals[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
