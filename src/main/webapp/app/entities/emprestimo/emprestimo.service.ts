import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmprestimo } from 'app/shared/model/emprestimo.model';

type EntityResponseType = HttpResponse<IEmprestimo>;
type EntityArrayResponseType = HttpResponse<IEmprestimo[]>;

@Injectable({ providedIn: 'root' })
export class EmprestimoService {
  public resourceUrl = SERVER_API_URL + 'api/emprestimos';

  constructor(protected http: HttpClient) {}

  create(emprestimo: IEmprestimo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emprestimo);
    return this.http
      .post<IEmprestimo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(emprestimo: IEmprestimo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(emprestimo);
    return this.http
      .put<IEmprestimo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmprestimo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmprestimo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(emprestimo: IEmprestimo): IEmprestimo {
    const copy: IEmprestimo = Object.assign({}, emprestimo, {
      dataEmprestimo:
        emprestimo.dataEmprestimo && emprestimo.dataEmprestimo.isValid() ? emprestimo.dataEmprestimo.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataEmprestimo = res.body.dataEmprestimo ? moment(res.body.dataEmprestimo) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((emprestimo: IEmprestimo) => {
        emprestimo.dataEmprestimo = emprestimo.dataEmprestimo ? moment(emprestimo.dataEmprestimo) : undefined;
      });
    }
    return res;
  }
}
