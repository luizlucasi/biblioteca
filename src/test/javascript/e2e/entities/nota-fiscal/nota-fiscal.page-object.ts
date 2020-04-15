import { element, by, ElementFinder } from 'protractor';

export class NotaFiscalComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-nota-fiscal div table .btn-danger'));
  title = element.all(by.css('jhi-nota-fiscal div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class NotaFiscalUpdatePage {
  pageTitle = element(by.id('jhi-nota-fiscal-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  numeroInput = element(by.id('field_numero'));
  competenciaInput = element(by.id('field_competencia'));
  tipoInput = element(by.id('field_tipo'));
  servicoInput = element(by.id('field_servico'));
  condicaoPagamentoInput = element(by.id('field_condicaoPagamento'));
  totalNFInput = element(by.id('field_totalNF'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNumeroInput(numero: string): Promise<void> {
    await this.numeroInput.sendKeys(numero);
  }

  async getNumeroInput(): Promise<string> {
    return await this.numeroInput.getAttribute('value');
  }

  async setCompetenciaInput(competencia: string): Promise<void> {
    await this.competenciaInput.sendKeys(competencia);
  }

  async getCompetenciaInput(): Promise<string> {
    return await this.competenciaInput.getAttribute('value');
  }

  async setTipoInput(tipo: string): Promise<void> {
    await this.tipoInput.sendKeys(tipo);
  }

  async getTipoInput(): Promise<string> {
    return await this.tipoInput.getAttribute('value');
  }

  async setServicoInput(servico: string): Promise<void> {
    await this.servicoInput.sendKeys(servico);
  }

  async getServicoInput(): Promise<string> {
    return await this.servicoInput.getAttribute('value');
  }

  async setCondicaoPagamentoInput(condicaoPagamento: string): Promise<void> {
    await this.condicaoPagamentoInput.sendKeys(condicaoPagamento);
  }

  async getCondicaoPagamentoInput(): Promise<string> {
    return await this.condicaoPagamentoInput.getAttribute('value');
  }

  async setTotalNFInput(totalNF: string): Promise<void> {
    await this.totalNFInput.sendKeys(totalNF);
  }

  async getTotalNFInput(): Promise<string> {
    return await this.totalNFInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class NotaFiscalDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-notaFiscal-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-notaFiscal'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
