import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LibraryTestModule } from '../../../test.module';
import { EmprestimoUpdateComponent } from 'app/entities/emprestimo/emprestimo-update.component';
import { EmprestimoService } from 'app/entities/emprestimo/emprestimo.service';
import { Emprestimo } from 'app/shared/model/emprestimo.model';

describe('Component Tests', () => {
  describe('Emprestimo Management Update Component', () => {
    let comp: EmprestimoUpdateComponent;
    let fixture: ComponentFixture<EmprestimoUpdateComponent>;
    let service: EmprestimoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibraryTestModule],
        declarations: [EmprestimoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EmprestimoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmprestimoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmprestimoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Emprestimo(123);
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
        const entity = new Emprestimo();
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
