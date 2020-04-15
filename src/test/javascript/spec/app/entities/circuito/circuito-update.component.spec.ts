import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LibraryTestModule } from '../../../test.module';
import { CircuitoUpdateComponent } from 'app/entities/circuito/circuito-update.component';
import { CircuitoService } from 'app/entities/circuito/circuito.service';
import { Circuito } from 'app/shared/model/circuito.model';

describe('Component Tests', () => {
  describe('Circuito Management Update Component', () => {
    let comp: CircuitoUpdateComponent;
    let fixture: ComponentFixture<CircuitoUpdateComponent>;
    let service: CircuitoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibraryTestModule],
        declarations: [CircuitoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CircuitoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CircuitoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CircuitoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Circuito(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Circuito();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
