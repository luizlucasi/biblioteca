import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { LibraryTestModule } from '../../../test.module';
import { CircuitoComponent } from 'app/entities/circuito/circuito.component';
import { CircuitoService } from 'app/entities/circuito/circuito.service';
import { Circuito } from 'app/shared/model/circuito.model';

describe('Component Tests', () => {
  describe('Circuito Management Component', () => {
    let comp: CircuitoComponent;
    let fixture: ComponentFixture<CircuitoComponent>;
    let service: CircuitoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibraryTestModule],
        declarations: [CircuitoComponent]
      })
        .overrideTemplate(CircuitoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CircuitoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CircuitoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Circuito(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.circuitos && comp.circuitos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
