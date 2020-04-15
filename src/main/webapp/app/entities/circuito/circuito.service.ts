import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICircuito } from 'app/shared/model/circuito.model';

type EntityResponseType = HttpResponse<ICircuito>;
type EntityArrayResponseType = HttpResponse<ICircuito[]>;

@Injectable({ providedIn: 'root' })
export class CircuitoService {
  public resourceUrl = SERVER_API_URL + 'api/circuitos';

  constructor(protected http: HttpClient) {}

  create(circuito: ICircuito): Observable<EntityResponseType> {
    return this.http.post<ICircuito>(this.resourceUrl, circuito, { observe: 'response' });
  }

  update(circuito: ICircuito): Observable<EntityResponseType> {
    return this.http.put<ICircuito>(this.resourceUrl, circuito, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICircuito>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICircuito[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
