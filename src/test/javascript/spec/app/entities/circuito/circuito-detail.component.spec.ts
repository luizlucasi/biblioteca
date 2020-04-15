import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LibraryTestModule } from '../../../test.module';
import { CircuitoDetailComponent } from 'app/entities/circuito/circuito-detail.component';
import { Circuito } from 'app/shared/model/circuito.model';

describe('Component Tests', () => {
  describe('Circuito Management Detail Component', () => {
    let comp: CircuitoDetailComponent;
    let fixture: ComponentFixture<CircuitoDetailComponent>;
    const route = ({ data: of({ circuito: new Circuito(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibraryTestModule],
        declarations: [CircuitoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CircuitoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CircuitoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load circuito on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.circuito).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
