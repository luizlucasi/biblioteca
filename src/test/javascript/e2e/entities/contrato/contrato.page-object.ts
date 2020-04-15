import { element, by, ElementFinder } from 'protractor';

export class ContratoComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-contrato div table .btn-danger'));
  title = element.all(by.css('jhi-contrato div h2#page-heading span')).first();
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

export class ContratoUpdatePage {
  pageTitle = element(by.id('jhi-contrato-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  codigoClienteSapInput = element(by.id('field_codigoClienteSap'));
  numeroContratoSapInput = element(by.id('field_numeroContratoSap'));
  numeroContratoTpzInput = element(by.id('field_numeroContratoTpz'));
  nomeInput = element(by.id('field_nome'));
  statusInput = element(by.id('field_status'));
  dataAssinaturaInput = element(by.id('field_dataAssinatura'));
  dataReajusteInput = element(by.id('field_dataReajuste'));
  dataTerminoInput = element(by.id('field_dataTermino'));
  estadoInput = element(by.id('field_estado'));
  cnpjInput = element(by.id('field_cnpj'));
  inscricaoEstadualInput = element(by.id('field_inscricaoEstadual'));
  vigenciaInput = element(by.id('field_vigencia'));

  notaFiscalSelect = element(by.id('field_notaFiscal'));
  circuitoSelect = element(by.id('field_circuito'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCodigoClienteSapInput(codigoClienteSap: string): Promise<void> {
    await this.codigoClienteSapInput.sendKeys(codigoClienteSap);
  }

  async getCodigoClienteSapInput(): Promise<string> {
    return await this.codigoClienteSapInput.getAttribute('value');
  }

  async setNumeroContratoSapInput(numeroContratoSap: string): Promise<void> {
    await this.numeroContratoSapInput.sendKeys(numeroContratoSap);
  }

  async getNumeroContratoSapInput(): Promise<string> {
    return await this.numeroContratoSapInput.getAttribute('value');
  }

  async setNumeroContratoTpzInput(numeroContratoTpz: string): Promise<void> {
    await this.numeroContratoTpzInput.sendKeys(numeroContratoTpz);
  }

  async getNumeroContratoTpzInput(): Promise<string> {
    return await this.numeroContratoTpzInput.getAttribute('value');
  }

  async setNomeInput(nome: string): Promise<void> {
    await this.nomeInput.sendKeys(nome);
  }

  async getNomeInput(): Promise<string> {
    return await this.nomeInput.getAttribute('value');
  }

  async setStatusInput(status: string): Promise<void> {
    await this.statusInput.sendKeys(status);
  }

  async getStatusInput(): Promise<string> {
    return await this.statusInput.getAttribute('value');
  }

  async setDataAssinaturaInput(dataAssinatura: string): Promise<void> {
    await this.dataAssinaturaInput.sendKeys(dataAssinatura);
  }

  async getDataAssinaturaInput(): Promise<string> {
    return await this.dataAssinaturaInput.getAttribute('value');
  }

  async setDataReajusteInput(dataReajuste: string): Promise<void> {
    await this.dataReajusteInput.sendKeys(dataReajuste);
  }

  async getDataReajusteInput(): Promise<string> {
    return await this.dataReajusteInput.getAttribute('value');
  }

  async setDataTerminoInput(dataTermino: string): Promise<void> {
    await this.dataTerminoInput.sendKeys(dataTermino);
  }

  async getDataTerminoInput(): Promise<string> {
    return await this.dataTerminoInput.getAttribute('value');
  }

  async setEstadoInput(estado: string): Promise<void> {
    await this.estadoInput.sendKeys(estado);
  }

  async getEstadoInput(): Promise<string> {
    return await this.estadoInput.getAttribute('value');
  }

  async setCnpjInput(cnpj: string): Promise<void> {
    await this.cnpjInput.sendKeys(cnpj);
  }

  async getCnpjInput(): Promise<string> {
    return await this.cnpjInput.getAttribute('value');
  }

  async setInscricaoEstadualInput(inscricaoEstadual: string): Promise<void> {
    await this.inscricaoEstadualInput.sendKeys(inscricaoEstadual);
  }

  async getInscricaoEstadualInput(): Promise<string> {
    return await this.inscricaoEstadualInput.getAttribute('value');
  }

  async setVigenciaInput(vigencia: string): Promise<void> {
    await this.vigenciaInput.sendKeys(vigencia);
  }

  async getVigenciaInput(): Promise<string> {
    return await this.vigenciaInput.getAttribute('value');
  }

  async notaFiscalSelectLastOption(): Promise<void> {
    await this.notaFiscalSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async notaFiscalSelectOption(option: string): Promise<void> {
    await this.notaFiscalSelect.sendKeys(option);
  }

  getNotaFiscalSelect(): ElementFinder {
    return this.notaFiscalSelect;
  }

  async getNotaFiscalSelectedOption(): Promise<string> {
    return await this.notaFiscalSelect.element(by.css('option:checked')).getText();
  }

  async circuitoSelectLastOption(): Promise<void> {
    await this.circuitoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async circuitoSelectOption(option: string): Promise<void> {
    await this.circuitoSelect.sendKeys(option);
  }

  getCircuitoSelect(): ElementFinder {
    return this.circuitoSelect;
  }

  async getCircuitoSelectedOption(): Promise<string> {
    return await this.circuitoSelect.element(by.css('option:checked')).getText();
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

export class ContratoDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-contrato-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-contrato'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
