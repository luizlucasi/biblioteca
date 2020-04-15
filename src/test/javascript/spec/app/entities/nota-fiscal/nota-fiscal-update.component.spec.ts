import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LibraryTestModule } from '../../../test.module';
import { NotaFiscalUpdateComponent } from 'app/entities/nota-fiscal/nota-fiscal-update.component';
import { NotaFiscalService } from 'app/entities/nota-fiscal/nota-fiscal.service';
import { NotaFiscal } from 'app/shared/model/nota-fiscal.model';

describe('Component Tests', () => {
  describe('NotaFiscal Management Update Component', () => {
    let comp: NotaFiscalUpdateComponent;
    let fixture: ComponentFixture<NotaFiscalUpdateComponent>;
    let service: NotaFiscalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibraryTestModule],
        declarations: [NotaFiscalUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NotaFiscalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NotaFiscalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NotaFiscalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NotaFiscal(123);
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
        const entity = new NotaFiscal();
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
