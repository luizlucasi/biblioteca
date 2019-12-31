import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LibraryTestModule } from '../../../test.module';
import { LivroUpdateComponent } from 'app/entities/livro/livro-update.component';
import { LivroService } from 'app/entities/livro/livro.service';
import { Livro } from 'app/shared/model/livro.model';

describe('Component Tests', () => {
  describe('Livro Management Update Component', () => {
    let comp: LivroUpdateComponent;
    let fixture: ComponentFixture<LivroUpdateComponent>;
    let service: LivroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibraryTestModule],
        declarations: [LivroUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LivroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LivroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LivroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Livro(123);
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
        const entity = new Livro();
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
