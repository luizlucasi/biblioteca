import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LibraryTestModule } from '../../../test.module';
import { AutorUpdateComponent } from 'app/entities/autor/autor-update.component';
import { AutorService } from 'app/entities/autor/autor.service';
import { Autor } from 'app/shared/model/autor.model';

describe('Component Tests', () => {
  describe('Autor Management Update Component', () => {
    let comp: AutorUpdateComponent;
    let fixture: ComponentFixture<AutorUpdateComponent>;
    let service: AutorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibraryTestModule],
        declarations: [AutorUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AutorUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AutorUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AutorService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Autor(123);
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
        const entity = new Autor();
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
