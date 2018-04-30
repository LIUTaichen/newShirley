import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatSort } from '@angular/material';
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
  displayedColumns = [
    'priority', 
    'plantNumber', 
    'quattraReference', 
    'plantDescription', 
    'job', 
    'location', 
    'locationDate',
    'repairNeeded',
    'state',
    'comments',
    'contractor',
    'hiredFrom',
    'daysOpened',
    'dateOpened',
    'dateCreated',
    'createdBy',
    'dateEdited',
    'editedBy'
    ];
  dataSource = new MatTableDataSource(this.niggles);
  @ViewChild(MatSort) sort: MatSort;

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
        this.dataSource.sort = this.sort;
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

