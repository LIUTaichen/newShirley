import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { Niggle } from '../niggle.model';
import { NiggleService } from '../niggle.service';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Principal } from '../../../shared';

@Component({
  selector: 'jhi-niggle-list-dw',
  templateUrl: './niggle-list-dw.component.html',
  styleUrls: ['./niggle-list-dw.component.css']
})
export class NiggleListDwComponent implements OnInit {

  niggles: Niggle[];
  displayedColumns = ['priority', 'plantNumber', 'quattraReference', 'plantDescription', 'job', 'location', 'locationDate',
    'repairNeeded',
    'state',
    'comments',
    'dateOpened',
    'daysOpended'];
  dataSource = new MatTableDataSource(this.niggles);


  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private niggleService: NiggleService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {
  }

  loadAll() {
    this.niggleService.query().subscribe(
      (res: HttpResponse<Niggle[]>) => {
        this.niggles = res.body;
        this.dataSource = new MatTableDataSource(this.niggles);
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }
  ngOnInit() {
    this.loadAll();
    this.principal.identity().then((account) => {
      this.currentAccount = account;
    });
    this.registerChangeInNiggles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: Niggle) {
    return item.id;
  }
  registerChangeInNiggles() {
    this.eventSubscriber = this.eventManager.subscribe('niggleListModification', (response) => this.loadAll());
  }

  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  getDaysOpened(niggle: Niggle) {
    if(niggle.dateOpened){
    return Math.floor(Math.abs(niggle.dateOpened.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24));
    }
    else{
      return null;
    }
  }

}

export interface Element {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

const ELEMENT_DATA: Element[] = [
  { position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H' },
  { position: 2, name: 'Helium', weight: 4.0026, symbol: 'He' },
  { position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li' },
  { position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be' },
  { position: 5, name: 'Boron', weight: 10.811, symbol: 'B' },
  { position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C' },
  { position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N' },
  { position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O' },
  { position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F' },
  { position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne' },
  { position: 11, name: 'Sodium', weight: 22.9897, symbol: 'Na' },
  { position: 12, name: 'Magnesium', weight: 24.305, symbol: 'Mg' },
  { position: 13, name: 'Aluminum', weight: 26.9815, symbol: 'Al' },
  { position: 14, name: 'Silicon', weight: 28.0855, symbol: 'Si' },
  { position: 15, name: 'Phosphorus', weight: 30.9738, symbol: 'P' },
  { position: 16, name: 'Sulfur', weight: 32.065, symbol: 'S' },
  { position: 17, name: 'Chlorine', weight: 35.453, symbol: 'Cl' },
  { position: 18, name: 'Argon', weight: 39.948, symbol: 'Ar' },
  { position: 19, name: 'Potassium', weight: 39.0983, symbol: 'K' },
  { position: 20, name: 'Calcium', weight: 40.078, symbol: 'Ca' },
];
