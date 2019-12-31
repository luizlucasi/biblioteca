import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAutor } from 'app/shared/model/autor.model';

type EntityResponseType = HttpResponse<IAutor>;
type EntityArrayResponseType = HttpResponse<IAutor[]>;

@Injectable({ providedIn: 'root' })
export class AutorService {
  public resourceUrl = SERVER_API_URL + 'api/autors';

  constructor(protected http: HttpClient) {}

  create(autor: IAutor): Observable<EntityResponseType> {
    return this.http.post<IAutor>(this.resourceUrl, autor, { observe: 'response' });
  }

  update(autor: IAutor): Observable<EntityResponseType> {
    return this.http.put<IAutor>(this.resourceUrl, autor, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAutor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAutor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
