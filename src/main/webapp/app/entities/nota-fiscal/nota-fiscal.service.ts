import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INotaFiscal } from 'app/shared/model/nota-fiscal.model';

type EntityResponseType = HttpResponse<INotaFiscal>;
type EntityArrayResponseType = HttpResponse<INotaFiscal[]>;

@Injectable({ providedIn: 'root' })
export class NotaFiscalService {
  public resourceUrl = SERVER_API_URL + 'api/nota-fiscals';

  constructor(protected http: HttpClient) {}

  create(notaFiscal: INotaFiscal): Observable<EntityResponseType> {
    return this.http.post<INotaFiscal>(this.resourceUrl, notaFiscal, { observe: 'response' });
  }

  update(notaFiscal: INotaFiscal): Observable<EntityResponseType> {
    return this.http.put<INotaFiscal>(this.resourceUrl, notaFiscal, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INotaFiscal>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INotaFiscal[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
