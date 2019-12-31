import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LibraryTestModule } from '../../../test.module';
import { AutorDetailComponent } from 'app/entities/autor/autor-detail.component';
import { Autor } from 'app/shared/model/autor.model';

describe('Component Tests', () => {
  describe('Autor Management Detail Component', () => {
    let comp: AutorDetailComponent;
    let fixture: ComponentFixture<AutorDetailComponent>;
    const route = ({ data: of({ autor: new Autor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LibraryTestModule],
        declarations: [AutorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AutorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AutorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load autor on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.autor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
