import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILivro } from 'app/shared/model/livro.model';

type EntityResponseType = HttpResponse<ILivro>;
type EntityArrayResponseType = HttpResponse<ILivro[]>;

@Injectable({ providedIn: 'root' })
export class LivroService {
  public resourceUrl = SERVER_API_URL + 'api/livros';

  constructor(protected http: HttpClient) {}

  create(livro: ILivro): Observable<EntityResponseType> {
    return this.http.post<ILivro>(this.resourceUrl, livro, { observe: 'response' });
  }

  update(livro: ILivro): Observable<EntityResponseType> {
    return this.http.put<ILivro>(this.resourceUrl, livro, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILivro>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILivro[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
