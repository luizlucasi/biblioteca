import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContrato } from 'app/shared/model/contrato.model';

type EntityResponseType = HttpResponse<IContrato>;
type EntityArrayResponseType = HttpResponse<IContrato[]>;

@Injectable({ providedIn: 'root' })
export class ContratoService {
  public resourceUrl = SERVER_API_URL + 'api/contratoes';

  constructor(protected http: HttpClient) {}

  create(contrato: IContrato): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contrato);
    return this.http
      .post<IContrato>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(contrato: IContrato): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contrato);
    return this.http
      .put<IContrato>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContrato>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContrato[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(contrato: IContrato): IContrato {
    const copy: IContrato = Object.assign({}, contrato, {
      dataAssinatura:
        contrato.dataAssinatura && contrato.dataAssinatura.isValid() ? contrato.dataAssinatura.format(DATE_FORMAT) : undefined,
      dataReajuste: contrato.dataReajuste && contrato.dataReajuste.isValid() ? contrato.dataReajuste.format(DATE_FORMAT) : undefined,
      dataTermino: contrato.dataTermino && contrato.dataTermino.isValid() ? contrato.dataTermino.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataAssinatura = res.body.dataAssinatura ? moment(res.body.dataAssinatura) : undefined;
      res.body.dataReajuste = res.body.dataReajuste ? moment(res.body.dataReajuste) : undefined;
      res.body.dataTermino = res.body.dataTermino ? moment(res.body.dataTermino) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((contrato: IContrato) => {
        contrato.dataAssinatura = contrato.dataAssinatura ? moment(contrato.dataAssinatura) : undefined;
        contrato.dataReajuste = contrato.dataReajuste ? moment(contrato.dataReajuste) : undefined;
        contrato.dataTermino = contrato.dataTermino ? moment(contrato.dataTermino) : undefined;
      });
    }
    return res;
  }
}
