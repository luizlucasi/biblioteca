import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { NotaFiscalService } from 'app/entities/nota-fiscal/nota-fiscal.service';
import { INotaFiscal, NotaFiscal } from 'app/shared/model/nota-fiscal.model';

describe('Service Tests', () => {
  describe('NotaFiscal Service', () => {
    let injector: TestBed;
    let service: NotaFiscalService;
    let httpMock: HttpTestingController;
    let elemDefault: INotaFiscal;
    let expectedResult: INotaFiscal | INotaFiscal[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(NotaFiscalService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new NotaFiscal(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a NotaFiscal', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new NotaFiscal()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a NotaFiscal', () => {
        const returnedFromService = Object.assign(
          {
            numero: 'BBBBBB',
            competencia: 'BBBBBB',
            tipo: 'BBBBBB',
            servico: 'BBBBBB',
            condicaoPagamento: 'BBBBBB',
            totalNF: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of NotaFiscal', () => {
        const returnedFromService = Object.assign(
          {
            numero: 'BBBBBB',
            competencia: 'BBBBBB',
            tipo: 'BBBBBB',
            servico: 'BBBBBB',
            condicaoPagamento: 'BBBBBB',
            totalNF: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a NotaFiscal', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
